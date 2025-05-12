import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  standalone: true,
  selector: 'app-shared-documents',
  templateUrl: './shared.component.html',
  styleUrls: ['./shared.component.css'],
  imports: [CommonModule, FormsModule]
})
export class SharedDocumentsComponent {
  searchTerm = '';

  sharedDocuments = [
    { id: 1, name: 'Contrat Client', type: 'PDF', sharedBy: 'Admin', sharedWith: 'User1', sharedAt: '2025-05-02' },
    { id: 2, name: 'Rapport Financier', type: 'Excel', sharedBy: 'User2', sharedWith: 'User3', sharedAt: '2025-05-04' },
    { id: 3, name: 'Présentation Projet', type: 'PowerPoint', sharedBy: 'User1', sharedWith: 'Admin', sharedAt: '2025-05-06' }
  ];

  get filteredDocuments() {
    return this.sharedDocuments.filter(doc =>
      doc.name.toLowerCase().includes(this.searchTerm.toLowerCase()) ||
      doc.sharedBy.toLowerCase().includes(this.searchTerm.toLowerCase()) ||
      doc.sharedWith.toLowerCase().includes(this.searchTerm.toLowerCase()) ||
      doc.type.toLowerCase().includes(this.searchTerm.toLowerCase())
    );
  }

  getFileIcon(type: string): string {
    switch (type.toLowerCase()) {
      case 'pdf': return '';
      case 'excel': return '';
      case 'word': return '';
      case 'powerpoint': return '';
      default: return '';
    }
  }

  downloadDocument(document: any) {
    alert(`Téléchargement de ${document.name}`);
    // ici tu ajoutes plus tard l'appel backend
  }
}
