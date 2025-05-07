import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class DocumentService {
  constructor(private http: HttpClient) {}

  // 🔁 Récupérer toutes les versions d’un document
  getVersions(documentId: string): Observable<any[]> {
    return this.http.get<any[]>(`/api/documents/${documentId}/versions`);
  }

  // ↩️ Restaurer une version donnée
  restoreVersion(documentId: string, versionLabel: string): Observable<void> {
    return this.http.post<void>(`/api/documents/${documentId}/restore`, { versionLabel });
  }

  // (facultatif) Tu peux aussi ajouter ici des méthodes de téléchargement ou d’ajout aux favoris si tu veux centraliser la logique
}
