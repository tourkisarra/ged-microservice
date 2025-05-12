import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NgChartsModule } from 'ng2-charts';
import { ChartData, ChartOptions } from 'chart.js';

@Component({
  standalone: true,
  selector: 'app-dashboard-admin',
  templateUrl: './admin-dashboard.component.html',
  styleUrls: ['./admin-dashboard.component.css'],
  imports: [CommonModule, NgChartsModule]
})
export class DashboardAdminComponent {

  pendingDocuments = 12;
  validatedDocuments = 80;
  rejectedDocuments = 8;
  activeUsers = 34;

  lineChartDataAdmin: ChartData<'line'> = {
    labels: ['Jan', 'Fév', 'Mars', 'Avr', 'Mai', 'Juin'],
    datasets: [
      {
        label: 'Documents validés',
        data: [5, 10, 8, 15, 12, 20],
        tension: 0.4,
        borderColor: '#0D4955',  // ✅ même couleur que User Dashboard
        backgroundColor: '#cce4e7', // ✅ remplissage doux
        pointBackgroundColor: '#0D4955',
        fill: true,
      }
    ]
  };

  lineChartOptionsAdmin: ChartOptions<'line'> = {
    responsive: true,
    plugins: {
      legend: {
        display: true,
        labels: {
          color: '#0D4955'
        }
      }
    },
    scales: {
      x: {
        ticks: { color: '#0D4955' },
        grid: { color: '#e2e8f0' }
      },
      y: {
        ticks: { color: '#0D4955' },
        grid: { color: '#e2e8f0' }
      }
    }
  };

  pieChartDataAdmin: ChartData<'pie'> = {
    labels: ['PDF', 'Word', 'Excel', 'Images'],
    datasets: [
      {
        data: [40, 30, 20, 10],
        backgroundColor: ['#0D4955', '#D9D9D9', '#b2d8d8', '#e0e0e0'],
        borderColor: ['white', 'white', 'white', 'white'],
        borderWidth: 2,
      }
    ]
  };

  pieChartOptionsAdmin: ChartOptions<'pie'> = {
    responsive: true,
    cutout: '70%', // ✅ Donne un effet "donut" propre
    plugins: {
      legend: {
        position: 'top',
        labels: {
          color: '#0D4955',
          font: { size: 12 }
        }
      }
    }
  };

}
