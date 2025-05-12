import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  standalone: true,
  selector: 'app-validation-documents',
  templateUrl: './validation-documents.component.html',
  styleUrls: ['./validation-documents.component.css'],
  imports: [CommonModule, FormsModule]
})
export class ValidationDocumentsComponent {

  documents = [
    { id: 1, name: 'Contrat 2025', type: 'PDF', creator: 'Sarah Tourki', created: new Date('2025-04-01') },
    { id: 2, name: 'Rapport Financier', type: 'Excel', creator: 'Ali Slim', created: new Date('2025-04-10') },
    { id: 3, name: 'Photo Evenement', type: 'Image', creator: 'Amira Khaldi', created: new Date('2025-04-15') },
  ];

  acceptDocument(doc: any) {
    if (confirm(`Confirmer la validation de "${doc.name}" ?`)) {
      this.documents = this.documents.filter(d => d.id !== doc.id);
      alert(`Document "${doc.name}" validé ✅`);
    }
  }

  rejectDocument(doc: any) {
    if (confirm(`Confirmer le rejet de "${doc.name}" ?`)) {
      this.documents = this.documents.filter(d => d.id !== doc.id);
      alert(`Document "${doc.name}" rejeté ❌`);
    }
  }

}
