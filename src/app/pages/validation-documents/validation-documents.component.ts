import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-validation-documents',
  templateUrl: './validation-documents.component.html',
  styleUrls: ['./validation-documents.component.css'],
  standalone: true,
  imports: [CommonModule]
})
export class ValidationDocumentsComponent {

  documents = [
    { id: 'DOC001', name: 'Contrat partenaire.pdf', type: 'PDF', submittedBy: 'Sarra Tourki', date: '2025-04-30', status: 'En attente' },
    { id: 'DOC002', name: 'Rapport financier.docx', type: 'Word', submittedBy: 'Sarra Tourki', date: '2025-04-29', status: 'En attente' },
    { id: 'DOC003', name: 'Présentation projet.pptx', type: 'PPT', submittedBy: 'Sarra Tourki', date: '2025-04-28', status: 'En attente' }
  ];

  isModalOpen = false;
  selectedDocument: any = null;
  actionType: 'validate' | 'reject' = 'validate';

  openConfirmationModal(document: any, action: 'validate' | 'reject') {
    this.selectedDocument = document;
    this.actionType = action;
    this.isModalOpen = true;
  }

  closeModal() {
    this.isModalOpen = false;
    this.selectedDocument = null;
  }

  confirmAction() {
    if (this.selectedDocument) {
      if (this.actionType === 'validate') {
        this.selectedDocument.status = 'Validé';
      } else if (this.actionType === 'reject') {
        this.selectedDocument.status = 'Rejeté';
      }
    }
    this.closeModal();
  }

}
