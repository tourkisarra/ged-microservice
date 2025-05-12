import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  standalone: true,
  selector: 'app-trash',
  templateUrl: './trash.component.html',
  styleUrls: ['./trash.component.css'],
  imports: [CommonModule, FormsModule]
})
export class TrashComponent {
  trashedDocuments = [
    { id: 1, name: 'Contrat Résilié', type: 'pdf', deletedAt: '2025-05-01' },
    { id: 2, name: 'Rapport Projet Annulé', type: 'word', deletedAt: '2025-05-04' },
    { id: 3, name: 'Ancienne facture', type: 'excel', deletedAt: '2025-05-06' }
  ];

  getFileIcon(type: string): string {
    switch (type.toLowerCase()) {
      case 'pdf': return '';
      case 'word': return '';
      case 'excel': return '';
      default: return '';
    }
  }

  restoreDocument(doc: any) {
    this.trashedDocuments = this.trashedDocuments.filter(d => d.id !== doc.id);
    alert(`Document "${doc.name}" restauré.`);
  }

  deletePermanently(doc: any) {
    if (confirm(` Supprimer définitivement "${doc.name}" ?`)) {
      this.trashedDocuments = this.trashedDocuments.filter(d => d.id !== doc.id);
      alert(` Document "${doc.name}" supprimé définitivement.`);
    }
  }

  emptyTrash() {
    if (confirm(' Êtes-vous sûr de vouloir vider toute la corbeille ? Cette action est irréversible.')) {
      this.trashedDocuments = [];
      alert(' La corbeille a été vidée.');
    }
  }
}
