import { Routes } from '@angular/router';
import { MyDocumentsComponent } from './pages/my-documents/my-documents.component';
import { DocumentViewerComponent } from './pages/document-viewer/document-viewer.component';
import { DocumentHistoryComponent } from './pages/document-history/document-history.component';

export const routes: Routes = [

  { path: 'my-documents', component: MyDocumentsComponent },
  { path: 'document-view/:id', component: DocumentViewerComponent },
  { path: 'document-history/:id', component: DocumentHistoryComponent },
  { path: '', redirectTo: '/my-documents', pathMatch: 'full' },


  { path: '', redirectTo: 'login', pathMatch: 'full' },
  { path: 'login', loadComponent: () => import('./pages/login/login.component').then(m => m.LoginComponent) },
  { path: 'reset-password', loadComponent: () => import('./pages/reset-password/reset-password.component').then(m => m.ResetPasswordComponent) },
  // User Pages
  { path: 'dashboard', loadComponent: () => import('./pages/dashboard/dashboard.component').then(m => m.DashboardComponent) },
  { path: 'recherche', loadComponent: () => import('./pages/my-documents/my-documents.component').then(m => m.MyDocumentsComponent) },
  { path: 'favoris', loadComponent: () => import('./pages/favoris/favoris.component').then(m => m.FavorisComponent) },
  { path: 'my-documents', loadComponent: () => import('./pages/my-documents/my-documents.component').then(m => m.MyDocumentsComponent) },
  { path: 'statistics', loadComponent: () => import('./pages/statistics/statistics.component').then(m => m.StatisticsComponent) },

  // Admin Pages
  {
    path: 'admin',
    children: [
      { path: 'dashboard', loadComponent: () => import('./pages/admin-dashboard/admin-dashboard.component').then(m => m.AdminDashboardComponent) },
      { path: 'validate-documents', loadComponent: () => import('./pages/validation-documents/validation-documents.component').then(m => m.ValidationDocumentsComponent) },
      { path: 'users', loadComponent: () => import('./pages/admin-users/admin-users.component').then(m => m.AdminUsersComponent) },
     { path: '', redirectTo: 'dashboard', pathMatch: 'full' },
    ]
  },

  // 404 fallback
  { path: '**', redirectTo: 'login' }
];
