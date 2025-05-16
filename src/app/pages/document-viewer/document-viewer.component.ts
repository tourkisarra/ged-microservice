import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { SidebarComponent } from '../../shared/sidebar/sidebar.component';

@Component({
  selector: 'app-document-viewer',
  standalone: true,
  imports: [CommonModule, SidebarComponent],
  templateUrl: './document-viewer.component.html',
  styleUrls: ['./document-viewer.component.css']
})
export class DocumentViewerComponent {
  document = {
    id: 'DOC-001',
    title: 'Contrat de prestation',
    creator: 'Sarra Tourki',
    createdAt: new Date('2024-12-10'),
    lastModified: new Date('2025-01-05'),
    type: 'PDF',
    fileSize: '1.4 Mo',
    description: 'Document de contrat pour la mission de développement.',
    isSigned: false
  };

  constructor(private router: Router) {}

  goBack(): void {
    this.router.navigate(['/document-history']); // adapte selon ta route exacte
  }
  renameDocument() {
  alert(' Renommer le document (fonction à implémenter)');
}

deleteDocument() {
  const confirmed = confirm(' Êtes-vous sûr de vouloir supprimer ce document ?');
  if (confirmed) {
    alert('Document supprimé (simulation)');
  }
}

openVersionHistory() {
  this.router.navigate(['/document-history', this.document.id]);
}

shareDocument() {
  alert(' Partage du document (simulation)');
}


  downloadDocument(): void {
    alert(' Téléchargement du document...');
    // Ici tu peux implémenter un vrai téléchargement via blob si besoin
  }

  addToFavorites(): void {
    alert(' Document ajouté aux favoris !');
  }

  signDocument(): void {
    this.document.isSigned = true;
    alert(' Document signé électroniquement.');
  }
}
