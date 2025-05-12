import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router'; // Ajouté pour la navigation

@Component({
  selector: 'app-my-documents',
  standalone: true,
  templateUrl: './my-documents.component.html',
  styleUrls: ['./my-documents.component.css'],
  imports: [CommonModule, FormsModule],
})
export class MyDocumentsComponent {
  documents: any[] = [];
  workspaces: any[] = [];
  favoriteDocuments: any[] = [];

  selectedWorkspaceName: string = '';
  searchTerm: string = '';
  searchDate: string = '';
  selectedStatus: string = '';

  newDocumentTitle: string = '';
  newDocumentType: string = '';
  newWorkspaceName: string = '';
  selectedFile: File | null = null;

  isFloatingMenuOpen: boolean = false;
  isUploadModalOpen: boolean = false;
  isCreateWorkspaceModalOpen: boolean = false;

  constructor(private router: Router) { // Injection du Router
    this.loadDummyData();
  }

  loadDummyData() {
    const documentA = { id: '1', title: 'Document A', type: 'PDF', creator: 'Admin', createdAt: new Date('2025-04-20'), status: '' };
    const documentB = { id: '2', title: 'Document B', type: 'Word', creator: 'User1', createdAt: new Date('2025-04-21'), status: '' };
    const documentC = { id: '3', title: 'Document C', type: 'Excel', creator: 'User2', createdAt: new Date('2025-04-22'), status: '' };

    this.documents = [documentA, documentB, documentC];

    this.workspaces = [
      { name: 'Workspace 1', documents: [documentA, documentB] },
      { name: 'Workspace 2', documents: [documentC] }
    ];

    this.favoriteDocuments = [documentA];
  }

  filteredDocuments() {
    return this.documents.filter(doc => {
      const matchesSearch = this.searchTerm
        ? doc.title.toLowerCase().includes(this.searchTerm.toLowerCase()) ||
          doc.type.toLowerCase().includes(this.searchTerm.toLowerCase()) ||
          doc.creator.toLowerCase().includes(this.searchTerm.toLowerCase())
        : true;

      const matchesDate = this.searchDate
        ? new Date(doc.createdAt).toISOString().slice(0, 10) === this.searchDate
        : true;

      const matchesStatus = this.selectedStatus
        ? doc.status === this.selectedStatus
        : true;

      const matchesWorkspace = this.selectedWorkspaceName
        ? this.isDocumentInWorkspace(doc, this.selectedWorkspaceName)
        : true;

      return matchesSearch && matchesDate && matchesStatus && matchesWorkspace;
    });
  }

  isDocumentInWorkspace(doc: any, workspaceName: string): boolean {
    const workspace = this.workspaces.find(ws => ws.name === workspaceName);
    return workspace ? workspace.documents.includes(doc) : false;
  }

  selectWorkspace(name: string) {
    this.selectedWorkspaceName = name;
  }

viewDocument(doc: any) {
  this.router.navigate(['/document-viewer', doc.id]);
}


  downloadDocument(doc: any) {
    console.log('Téléchargement du document:', doc.title);
    // Implémentation réelle du téléchargement ici
  }

  viewDocumentHistory(doc: any) {
    this.router.navigate(['/document-history', doc.id]); // Navigation vers document-history
  }

  toggleFloatingMenu() {
    this.isFloatingMenuOpen = !this.isFloatingMenuOpen;
  }

  openUploadModal() {
    this.closeModals();
    this.isUploadModalOpen = true;
  }

  openCreateWorkspaceModal() {
    this.closeModals();
    this.isCreateWorkspaceModalOpen = true;
  }

  closeModals() {
    this.isUploadModalOpen = false;
    this.isCreateWorkspaceModalOpen = false;
  }

  onFileSelected(event: any) {
    if (event.target.files && event.target.files.length > 0) {
      this.selectedFile = event.target.files[0];
      console.log('Fichier sélectionné:', this.selectedFile?.name);
    }
  }

  uploadDocument() {
    if (!this.newDocumentTitle || !this.newDocumentType || !this.selectedFile) {
      console.error('Veuillez remplir tous les champs');
      return;
    }
    console.log('Document à uploader:', this.newDocumentTitle, this.newDocumentType, this.selectedFile.name);
    this.closeModals();
  }

  createWorkspace() {
    if (!this.newWorkspaceName) {
      console.error('Veuillez renseigner un nom pour le Workspace');
      return;
    }
    console.log('Création du workspace:', this.newWorkspaceName);
    this.closeModals();
  }
}