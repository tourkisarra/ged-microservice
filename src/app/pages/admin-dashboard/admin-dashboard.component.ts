import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { NgChartsModule } from 'ng2-charts';
import { ChartOptions } from 'chart.js';
@Component({
  selector: 'app-admin-dashboard',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    NgChartsModule  // ✅ Obligatoire ici !
  ],
  templateUrl: './admin-dashboard.component.html'
})

export class AdminDashboardComponent {

  pendingDocuments = 12;
  validatedDocuments = 80;
  rejectedDocuments = 8;
  activeUsers = 34;

  // Line Chart Data
  lineChartData = {
    labels: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun'],
    datasets: [
      {
        data: [5, 15, 10, 20, 18, 30],
        label: 'Documents validés',
        fill: true,
        tension: 0.4,
        borderColor: '#0D4955',
        backgroundColor: 'rgba(13, 73, 85, 0.2)',
        pointBackgroundColor: '#0D4955',
      }
    ]
  };

  lineChartOptions: ChartOptions = {
    responsive: true,
    maintainAspectRatio: false
  };

  // Pie Chart Data
  pieChartData = {
    labels: ['PDF', 'Word', 'Excel', 'Images'],
    datasets: [{
      data: [40, 25, 20, 15],
      backgroundColor: ['#4CAF50', '#2196F3', '#FFC107', '#FF5722'],
    }]
  };

  pieChartOptions: ChartOptions = {
    responsive: true,
    maintainAspectRatio: false
  };

}
