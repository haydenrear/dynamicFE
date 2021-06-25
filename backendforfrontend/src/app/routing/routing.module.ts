import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {FormComponent} from "../form/form.component";
import {LoginComponent} from "../login/login.component";


const routes: Routes = [
  {path: 'parse', component: FormComponent},
  {path: 'login', component: LoginComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class RoutingRoutingModule {
}
