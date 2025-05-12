import { Routes } from '@angular/router';
import { MyDocumentsComponent } from './pages/my-documents/my-documents.component';
import { DocumentHistoryComponent } from './pages/document-history/document-history.component';
import { DocumentViewerComponent } from './pages/document-viewer/document-viewer.component';


export const routes: Routes = [

  // Routes principales (statiques)
  { path: 'my-documents', component: MyDocumentsComponent },
  { path: 'document-history/:id', component: DocumentHistoryComponent },
     { path: 'document-viewer/:id', component: DocumentViewerComponent },
   
  // Auth
  { path: 'login', loadComponent: () => import('./pages/login/login.component').then(m => m.LoginComponent) },
  { path: 'reset-password', loadComponent: () => import('./pages/reset-password/reset-password.component').then(m => m.ResetPasswordComponent) },

  // Pages utilisateur
  { path: 'dashboard', loadComponent: () => import('./pages/dashboard/dashboard.component').then(m => m.DashboardUserComponent) },
  { path: 'recherche', loadComponent: () => import('./pages/my-documents/my-documents.component').then(m => m.MyDocumentsComponent) },
  { path: 'favoris', loadComponent: () => import('./pages/favoris/favoris.component').then(m => m.FavorisComponent) },
  { path: 'statistics', loadComponent: () => import('./pages/statistics/statistics.component').then(m => m.StatisticsComponent) },
  { path: 'trash', loadComponent: () => import('./pages/trash/trash.component').then(m => m.TrashComponent) },
  { path: 'shared-documents', loadComponent: () => import('./pages/shared/shared.component').then(m => m.SharedDocumentsComponent) },
  { path: 'document-detail/:id', loadComponent: () => import('./pages/document-detail/document-detail.component').then(m => m.DocumentDetailComponent) },
  { path: 'share-document/:id', loadComponent: () => import('./pages/share-document/share-document.component').then(m => m.ShareDocumentComponent) },

  // Pages admin
  {
    path: 'admin',
    children: [
      { path: 'dashboard', loadComponent: () => import('./pages/admin-dashboard/admin-dashboard.component').then(m => m.DashboardAdminComponent) },
      { path: 'validate-documents', loadComponent: () => import('./pages/validation-documents/validation-documents.component').then(m => m.ValidationDocumentsComponent) },
      { path: 'users', loadComponent: () => import('./pages/admin-users/admin-users.component').then(m => m.AdminUsersComponent) },
      { path: '', redirectTo: 'dashboard', pathMatch: 'full' },
    ]
  },

  // ✅ Redirection unique à la racine
  { path: '', redirectTo: 'my-documents', pathMatch: 'full' },

  // ❌ 404 - fallback
  { path: '**', redirectTo: 'login' }
];
