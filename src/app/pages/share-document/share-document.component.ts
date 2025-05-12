import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  standalone: true,
  selector: 'app-share-document',
  templateUrl: './share-document.component.html',
  styleUrls: ['./share-document.component.css'],
  imports: [CommonModule, FormsModule]
})
export class ShareDocumentComponent {
  documentId: string | null = null;
  selectedUser: string = '';

  availableUsers = ['User1', 'User2', 'User3', 'Admin'];

  constructor(private route: ActivatedRoute) {
    this.documentId = this.route.snapshot.paramMap.get('id');
  }

  share() {
    if (this.selectedUser) {
      alert(`Document ${this.documentId} partagé avec ${this.selectedUser} ✅`);
      // ici tu appelleras ton API backend pour vraiment partager
    } else {
      alert('Veuillez sélectionner un utilisateur.');
    }
  }
}
