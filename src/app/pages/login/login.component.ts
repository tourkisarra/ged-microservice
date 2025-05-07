import { Component } from '@angular/core';
import { RouterLink } from '@angular/router'; // ✅ il faut importer RouterLink

@Component({
  standalone: true,
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
  imports: [RouterLink] // ✅ il faut le mettre ici aussi
})
export class LoginComponent {}
