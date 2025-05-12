import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  standalone: true,
  selector: 'app-document-detail',
  templateUrl: './document-detail.component.html',
  styleUrls: ['./document-detail.component.css'],
  imports: [CommonModule]
})
export class DocumentDetailComponent {
  documentId: string | null = null;

  documentData: any = null;

  // Simuler récupération document par id
  documents = [
    { id: '1', name: 'Contrat Client', type: 'PDF', sharedBy: 'Admin', sharedWith: 'User1', sharedAt: '2025-05-02', description: 'Contrat signé avec un client important.' },
    { id: '2', name: 'Rapport Financier', type: 'Excel', sharedBy: 'User2', sharedWith: 'User3', sharedAt: '2025-05-04', description: 'Analyse financière annuelle.' }
  ];

  constructor(private route: ActivatedRoute) {
    this.documentId = this.route.snapshot.paramMap.get('id');
    this.loadDocument();
  }

  loadDocument() {
    this.documentData = this.documents.find(d => d.id === this.documentId);
  }
}
