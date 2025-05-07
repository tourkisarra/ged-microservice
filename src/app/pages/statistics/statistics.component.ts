import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NgChartsModule } from 'ng2-charts'; // ✅ Très important pour <canvas baseChart>
import { RouterModule } from '@angular/router'; // ✅ Pour routerLink

import { ChartType, ChartData, ChartOptions } from 'chart.js';

@Component({
  selector: 'app-statistics',
  standalone: true,
  imports: [CommonModule, RouterModule, NgChartsModule], // ✅ Attention ici aussi
  templateUrl: './statistics.component.html',
  styleUrls: ['./statistics.component.css']
})
export class StatisticsComponent {
  lineChartData: ChartData<'line'> = {
    labels: ['Jan', 'Fév', 'Mar', 'Avr', 'Mai', 'Juin'],
    datasets: [
      {
        data: [10, 25, 18, 22, 30, 38],
        label: 'Documents',
        tension: 0.4,
        fill: true,
        borderColor: '#0D4955',
        backgroundColor: 'rgba(13,73,85,0.2)',
        pointBackgroundColor: '#0D4955'
      }
    ]
  };
  lineChartOptions: ChartOptions<'line'> = { responsive: true, maintainAspectRatio: false };

  doughnutChartData: ChartData<'doughnut'> = {
    labels: ['Images', 'Documents', 'Vidéos', 'Autres'],
    datasets: [
      {
        data: [30, 45, 15, 10],
        backgroundColor: ['#0D4955', '#1E6776', '#62B3C7', '#BFDDE3']
      }
    ]
  };
  doughnutChartOptions: ChartOptions<'doughnut'> = { responsive: true, maintainAspectRatio: false };
  doughnutChartType: 'doughnut' = 'doughnut';

  barChartData: ChartData<'bar'> = {
    labels: ['Lundi', 'Mardi', 'Mercredi', 'Jeudi', 'Vendredi'],
    datasets: [
      {
        data: [12, 19, 22, 30, 25],
        label: 'Consultations',
        backgroundColor: '#0D4955'
      }
    ]
  };
  barChartOptions: ChartOptions<'bar'> = { responsive: true, maintainAspectRatio: false };

  activeUsers = 34;
}
