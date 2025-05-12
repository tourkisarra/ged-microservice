import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  standalone: true,
  selector: 'app-admin-users',
  templateUrl: './admin-users.component.html',
  styleUrls: ['./admin-users.component.css'],
  imports: [CommonModule, FormsModule]
})
export class AdminUsersComponent {
  users = [
    { id: 1, name: 'Amira Salah', email: 'amira@example.com', organization: 'One Gate Africa', role: 'Administrateur', status: 'Active' },
    { id: 2, name: 'Mohamed Ben Ali', email: 'mohamed@example.com', organization: 'One Gate Africa', role: 'Utilisateur', status: 'Inactive' },
    { id: 3, name: 'Sarah Jebali', email: 'sarah@example.com', organization: 'One Gate Africa', role: 'Utilisateur', status: 'Active' },
  ];

  showModal = false;
  newUser = { name: '', email: '', organization: '', role: '', status: 'Active' };

  pageSize = 5;
  currentPage = 1;

  get paginatedUsers() {
    const start = (this.currentPage - 1) * this.pageSize;
    return this.users.slice(start, start + this.pageSize);
  }

  get totalPages() {
    return Math.ceil(this.users.length / this.pageSize);
  }

  getInitials(name: string): string {
    return name.split(' ').map(n => n.charAt(0)).join('').substring(0, 2).toUpperCase();
  }

  toggleStatus(user: any) {
    user.status = (user.status === 'Active') ? 'Inactive' : 'Active';
  }

  deleteUser(user: any) {
    if (confirm(`Supprimer ${user.name} ?`)) {
      this.users = this.users.filter(u => u.id !== user.id);
    }
  }

  openModal() {
    this.showModal = true;
    this.newUser = { name: '', email: '', organization: '', role: '', status: 'Active' };
  }

  closeModal() {
    this.showModal = false;
  }

  addUser() {
    const newId = this.users.length > 0 ? Math.max(...this.users.map(u => u.id)) + 1 : 1;
    this.users.push({ id: newId, ...this.newUser });
    this.closeModal();
  }

  submitForm() {
    this.addUser();
  }

  goToPage(page: number) {
    this.currentPage = page;
  }
}
