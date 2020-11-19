package com.osi.ftpapplication.service;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.util.List;
import java.util.Vector;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import com.osi.ftpapplication.exception.FTPLocationNotFoundException;
import com.osi.ftpapplication.model.FTPLocation;

import com.osi.ftpapplication.repository.FTPLocationRepository;

@Service
public class FTPLocationService {

	FTPLocation ftpLocation;

	@Autowired
	FTPLocationRepository ftpLocationRepository;

	public String createFTPLocation(FTPLocation ftpLocation) {
		FTPLocation savedFTPLocation = ftpLocationRepository.save(ftpLocation);
		if (savedFTPLocation != null)
			return "Your FTPLoaction is saved " + ftpLocation.getUserName();
		else
			return "Your FTPLoaction isn't saved " + ftpLocation.getUserName();
	}

	public FTPLocation getFTPLocation(String locationName) {
		return ftpLocationRepository.getByLocationName(locationName);
	}

	public FTPLocation getFTPLocation(Integer id) {
		// TODO Auto-generated method stub
		return ftpLocationRepository.getOne(id);
	}

	public FTPLocation updateFTPLocation(Integer id, FTPLocation ftpLocation) throws FTPLocationNotFoundException {
		if (!ftpLocationRepository.existsById(id)) {
			throw new FTPLocationNotFoundException("FTP doesn't exist " + id);
		}
		FTPLocation updatedFTPLocation = new FTPLocation();
		updatedFTPLocation.setId(id);
		updatedFTPLocation.setLocationName(ftpLocation.getLocationName());
		updatedFTPLocation.setPort(ftpLocation.getPort());
		updatedFTPLocation.setServer(ftpLocation.getServer());
		updatedFTPLocation.setUserName(ftpLocation.getUserName());
		updatedFTPLocation.setPassword(ftpLocation.getPassword());
		updatedFTPLocation.setDirectory(ftpLocation.getDirectory());
		return ftpLocationRepository.save(updatedFTPLocation);
	}

	public List<FTPLocation> getAllFTPLocations() {
		List<FTPLocation> AllFTPLocation = null;
		AllFTPLocation = ftpLocationRepository.findAll();
		return AllFTPLocation;
	}

	public FTPLocation deleteFTPLocation(Integer id) {
		ftpLocationRepository.deleteById(id);
		return null;
	}

	// testing ftplocation

	public String testFTPLocation(String hostName, Integer port, String userName, String pw) {

		try {
			String pwdDecode = URLDecoder.decode(pw, "UTF-8");
			System.out.println(pwdDecode);

			JSch jsch = new JSch();

			Session session = jsch.getSession(userName, hostName, port);
			session.setConfig("StrictHostKeyChecking", "no");
			session.setPassword(pw);
			session.connect();
			return "SUCCESS";
		} catch (Exception e) {
			return "FAIL";
		}
	}

	public String testFTPLocation(Integer locationID) {

		FTPLocation ftpLocation = ftpLocationRepository.getOne(locationID);
		return testFTPLocation(ftpLocation.getServer(), ftpLocation.getPort(), ftpLocation.getUserName(),
				ftpLocation.getPassword());

	}

	public List<String> getFilesFTPFromServer(ChannelSftp channelSftp, String SFTPWORKINGDIR) {

		//// Return list of files present in dirPath
		/// Eg list [monika/abc.txt, Jagadeesh/xyz.txt]
		List someDataList = new Vector<>();

		try {

			channelSftp.cd(SFTPWORKINGDIR);
			Vector filelist = channelSftp.ls(SFTPWORKINGDIR);

			for (int i = 0; i < filelist.size(); i++) {

				String fileDetails = filelist.get(i).toString();

				String[] stringArr = fileDetails.split(" ");

				String fileName = stringArr[stringArr.length - 1];
				if ((!fileName.equalsIgnoreCase(".")) && (!fileName.equalsIgnoreCase(".."))) {
//					if(fileName.endsWith(".txt") && fileName.endsWith(".py") && fileName.endsWith(".java")) {
					someDataList.add(fileName);
//				}
				}
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return someDataList;
	}

	public ChannelSftp getsftpChannel(Integer locationID) throws SftpException {

		ChannelSftp sftpChannel = null;
		JSch jsch = new JSch();
		Session session = null;
		try {
			FTPLocation ftpLocation = ftpLocationRepository.getOne(locationID);
			session = jsch.getSession(ftpLocation.getUserName(), ftpLocation.getServer(), ftpLocation.getPort());
			session.setConfig("StrictHostKeyChecking", "no");
			session.setPassword(ftpLocation.getPassword());
			session.connect();
			Channel channel = session.openChannel("sftp");
			channel.connect();
			sftpChannel = (ChannelSftp) channel;
			/// We have to close the Session Object
			/// session.disconnect();
		} catch (JSchException e) {
			e.printStackTrace();
		}

		return sftpChannel;
	}

	private boolean copyFile(InputStream sourceInputStream, ChannelSftp destSFTPChannel, String destPath)
			// public Boolean copyFile(Integer id, String sourceInputStream, String
			// destInputStream)
			throws IOException, SftpException {

		BufferedReader br = new BufferedReader(new InputStreamReader(sourceInputStream));

		String temp;
		while ((temp = br.readLine()) != null) {
			InputStream tempStream = new ByteArrayInputStream(temp.getBytes());
			destSFTPChannel.put(tempStream, destPath, 1);
		}
		return true;

	}

	public boolean copyFiles(Integer sourceID, Integer destID) {

		try {
			ChannelSftp sourceChannel = getsftpChannel(sourceID);
			ChannelSftp destChannel = getsftpChannel(destID);

			String sourceDir = ftpLocationRepository.getOne(sourceID).getDirectory();
			String destinationDir = ftpLocationRepository.getOne(destID).getDirectory();

			List<String> sourceFileList = getFilesFTPFromServer(sourceChannel, sourceDir);

			for (String fileName : sourceFileList) {
				InputStream sourceIS = sourceChannel.get(sourceDir + "/" + fileName);
				String destCompletePath = destinationDir + "/" + fileName;
				copyFile(sourceIS, destChannel, destCompletePath);
			}
			sourceChannel.disconnect();
			sourceChannel.exit();
			destChannel.disconnect();
			destChannel.exit();
			return true;
		} catch (Exception e) {
			System.out.println(e.toString());

		}
		return false;
	}

}
