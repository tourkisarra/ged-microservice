import { Component } from '@angular/core';
import { Router, RouterOutlet } from '@angular/router'; 
import { CommonModule } from '@angular/common'; // ✅ ici
import { SidebarComponent } from './shared/sidebar/sidebar.component';
import { AdminSidebarComponent } from './shared/admin-sidebar/admin-sidebar.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [
    CommonModule, 
    RouterOutlet,
    SidebarComponent,
    AdminSidebarComponent
  ],
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  constructor(public router: Router) {}

  isAuthPage(): boolean {
    return this.router.url.startsWith('/login') || this.router.url.startsWith('/reset-password');
  }

  isAdminPage(): boolean {
    return this.router.url.startsWith('/admin');
  }

  isUserPage(): boolean {
    return !this.isAuthPage() && !this.isAdminPage();
  }
}
