import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { CommonModule } from '@angular/common';
import { SidebarComponent } from '../../shared/sidebar/sidebar.component';

@Component({
  selector: 'app-document-history',
  standalone: true,
  imports: [CommonModule, SidebarComponent],
  templateUrl: './document-history.component.html',
  styleUrls: ['./document-history.component.css']
})
export class DocumentHistoryComponent implements OnInit {
  documentId!: string;
  versions: any[] = [];

  constructor(private route: ActivatedRoute) {}

  ngOnInit(): void {
    this.documentId = this.route.snapshot.paramMap.get('id')!;
    console.log('Chargement des versions pour :', this.documentId);
    this.loadHistory();
  }

  loadHistory(): void {
    //  Simulation sans appel backend
    this.versions = [
      { versionLabel: 'v1.0', modified: new Date('2025-06-10'), author: 'Sarra' },
      { versionLabel: 'v1.1', modified: new Date('2025-06-12'), author: 'Sarra' },
      { versionLabel: 'v2.0', modified: new Date('2025-06-15'), author: 'Sarra' }
    ];
  }

  restoreVersion(version: any): void {
    alert(`Version ${version.versionLabel} restaurée.`);
  }
}
