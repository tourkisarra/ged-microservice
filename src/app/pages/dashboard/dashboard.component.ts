import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NgChartsModule } from 'ng2-charts';
import { ChartData, ChartOptions } from 'chart.js';

@Component({
  standalone: true,
  selector: 'app-dashboard-user',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css'],
  imports: [CommonModule, NgChartsModule]
})
export class DashboardUserComponent {

  documentCount = 150;
  workspaceCount = 12;
  favoriteCount = 25;

  lineChartData: ChartData<'line'> = {
    labels: ['Jan', 'Fév', 'Mars', 'Avr', 'Mai', 'Juin'],
    datasets: [
      {
        label: 'Documents',
        data: [5, 15, 10, 20, 25, 35],
        tension: 0.4,
        borderColor: '#0D4955',  // ✅ couleur ligne
        backgroundColor: '#cce4e7',  // ✅ couleur remplissage zone
        pointBackgroundColor: '#0D4955',
        fill: true,
      }
    ]
  };

  lineChartOptions: ChartOptions<'line'> = {
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

  doughnutChartData: ChartData<'doughnut'> = {
    labels: ['Images', 'Documents', 'Vidéos', 'Autres'],
    datasets: [
      {
        data: [40, 30, 20, 10],
        backgroundColor: ['#0D4955', '#D9D9D9', '#b2d8d8', '#e0e0e0'],
        borderColor: ['white', 'white', 'white', 'white'],
        borderWidth: 2,
      }
    ]
  };

  doughnutChartOptions: ChartOptions<'doughnut'> = {
    responsive: true,
    cutout: '70%',
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
