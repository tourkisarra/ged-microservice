import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { CommonModule } from '@angular/common';

interface Document {
  id: string;
  title: string;
  description: string;
  creator: string;
  fileSize: string;
  createdAt: Date;
  lastModified: Date;
  type: 'Document' | 'Image' | 'Video' | 'Other';
  isSigned: boolean; // ➔ Ajout de champ signature
}

@Component({
  selector: 'app-document-viewer',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './document-viewer.component.html',
  styleUrls: ['./document-viewer.component.css']
})
export class DocumentViewerComponent {
  document!: Document;

  constructor(private route: ActivatedRoute, private router: Router) {
    const id = this.route.snapshot.paramMap.get('id');
    // Simulation de document
    this.document = {
      id: id || 'DOC001',
      title: 'MockupWeb-2.jpg',
      description: 'Image UI/UX design',
      creator: 'Sarra Tourki',
      fileSize: '2.5 MB',
      createdAt: new Date('2025-06-18'),
      lastModified: new Date('2025-06-20'),
      type: 'Image',
      isSigned: false // initialement pas signé
    };
  }

  goBack(): void {
    this.router.navigate(['/my-documents']);
  }

  downloadDocument(): void {
    alert(`📥 Téléchargement de : ${this.document.title}`);
    // 🔄 À connecter plus tard avec API backend
  }

  addToFavorites(): void {
    alert(`❤️ ${this.document.title} ajouté aux favoris`);
    // 🔄 À connecter plus tard avec backend ou stockage local
  }

  // ➡️ Signer électroniquement
  signDocument(): void {
    const confirmed = confirm('✅ Voulez-vous signer électroniquement ce document ?');
    if (confirmed) {
      this.document.isSigned = true;
      alert(`🖋️ Document "${this.document.title}" signé avec succès !`);
    }
  }
}
