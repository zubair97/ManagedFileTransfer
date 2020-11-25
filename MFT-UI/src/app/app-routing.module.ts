import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { HomeComponent } from './home/home.component';
import { SchedulerComponent } from './scheduler/scheduler.component';
import { LocationComponent } from './location/location.component';
import { CreateLocationComponent } from './location/create-location/create-location.component';
import { CreateSchedulerComponent } from './scheduler/create-scheduler/create-scheduler.component';
import { JobHistoryComponent } from './home/job-history/job-history.component';

const routes: Routes = [
  { path: 'login', component: LoginComponent },
  {
    path: 'home', component: HomeComponent,
    children: [
      {
        path: 'scheduler', component: SchedulerComponent,
        children: [
          { path: 'create-scheduler', component: CreateSchedulerComponent }
        ]
      },
      {
        path: 'location', component: LocationComponent,
        children: [
          { path: 'create-location', component: CreateLocationComponent }
        ]
      },
      {
        path: 'job-history', component: JobHistoryComponent
      },
      { path: '**', redirectTo: 'job-history' }
    ]
  },
  { path: '**', redirectTo: 'login' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
