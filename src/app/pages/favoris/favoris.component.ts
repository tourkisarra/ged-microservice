import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SidebarComponent } from '../../shared/sidebar/sidebar.component';
import { RouterModule, Router } from '@angular/router';

interface Document {
  id: string;
  title: string;
  description: string;
  creator: string;
  fileSize: string;
  createdAt: Date;
  lastModified: Date;
  type: 'Document' | 'Image' | 'Video' | 'Other';
  isFavorite: boolean;
}

@Component({
  selector: 'app-favoris',
  standalone: true,
  imports: [CommonModule, RouterModule], // ❌ SUPPRIMER SidebarComponent
  templateUrl: './favoris.component.html',
  styleUrls: ['./favoris.component.css']
})

export class FavorisComponent {

  // Liste des documents favoris simulée
  favoris: Document[] = [
    {
      id: 'DOC001',
      title: 'MockupWeb-2.jpg',
      description: 'Image UI/UX',
      creator: 'Sarra Tourki',
      fileSize: '2.5 MB',
      createdAt: new Date('2025-06-18'),
      lastModified: new Date('2025-06-20'),
      type: 'Image',
      isFavorite: true
    },
    {
      id: 'DOC003',
      title: 'TextUpdate-1.doc',
      description: 'Document texte',
      creator: 'Sarra Tourki',
      fileSize: '1.2 MB',
      createdAt: new Date('2025-06-15'),
      lastModified: new Date('2025-06-18'),
      type: 'Document',
      isFavorite: true
    }
  ];

  constructor(private router: Router) {}

  // Navigue vers le viewer
  viewDocument(doc: Document): void {
    this.router.navigate(['/documents', doc.id]);
  }

  // Retirer un document des favoris
  removeFromFavorites(doc: Document): void {
    this.favoris = this.favoris.filter(d => d.id !== doc.id);
  }

  // Télécharger un document
  downloadDocument(doc: Document): void {
    // Simule un téléchargement
    alert(`⬇️ Téléchargement du document : ${doc.title}`);
  }
}
