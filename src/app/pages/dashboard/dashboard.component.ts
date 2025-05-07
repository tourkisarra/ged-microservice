import { Component } from '@angular/core';
import { ChartData, ChartOptions } from 'chart.js';

@Component({
  selector: 'app-dashboard',
  standalone: true, // ✅ standalone ok
  templateUrl: './dashboard.component.html', // ✅ pas de imports: []
})
export class DashboardComponent {

  documentCount = 150;
  workspaceCount = 12;
  favoriteCount = 25;

  // 📈 Graphique ligne
  lineChartData: ChartData<'line'> = {
    labels: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul'],
    datasets: [{
      label: 'Documents',
      data: [15, 20, 10, 30, 40, 35, 50],
      borderColor: '#0D4955',
      backgroundColor: 'rgba(13, 73, 85, 0.3)',
      fill: true,
      tension: 0.4
    }]
  };

  lineChartOptions: ChartOptions<'line'> = {
    responsive: true,
    plugins: {
      legend: { position: 'top' }
    },
    scales: {
      x: {},
      y: { beginAtZero: true }
    }
  };

  // 🍩 Graphique doughnut
  doughnutChartData: ChartData<'doughnut'> = {
    labels: ['PDF', 'Word', 'Excel', 'Image'],
    datasets: [{
      label: 'Type de Documents',
      data: [40, 25, 20, 15],
      backgroundColor: [
        '#4CAF50', '#2196F3', '#FFC107', '#FF5722'
      ],
      hoverOffset: 10
    }]
  };

  doughnutChartOptions: ChartOptions<'doughnut'> = {
    responsive: true,
    plugins: {
      legend: { position: 'bottom' },
      tooltip: { enabled: true }
    }
  };

}
