import {
  Component,
  Input,
  OnChanges,
  OnInit,
  SimpleChanges,
} from '@angular/core';
import Chart from 'chart.js/auto';
import { DataToChart } from 'src/app/models/user.model';
import gradient from 'chartjs-plugin-gradient';
Chart.defaults.font.size = 12;
Chart.defaults.font.family = "'Alata', 'sans-serif'";
@Component({
  selector: 'app-chart',
  templateUrl: './chart.component.html',
  styleUrls: ['./chart.component.scss'],
})
export class ChartComponent implements OnInit, OnChanges {
  @Input({ required: true }) data!: DataToChart[];

  ngOnInit(): void {
    this.createChart();
  }
  chart: any;

  createChart() {
    this.chart = new Chart('progress', {
      type: 'line',

      data: {
        labels: this.data.map((item) => item.date),
        datasets: [
          {
            label: 'Sales',
            data: this.data.map((item) => item.value.toString()),
            fill: true,
            gradient: {
              backgroundColor: {
                axis: 'y',
                colors: {
                  0: 'rgba(225,29,72,0.3715861344537815)',
                  100: 'rgba(225,29,72,0.78)',
                },
              },
            },
            borderColor: '#e11d48',
            borderWidth: 1,
            tension: 0.2,
          },
        ],
      },
      options: {
        responsive: true,
        plugins: {
          legend: {
            display: false,
          },
        },
      },
      plugins: [gradient],
    });
  }

  ngOnChanges(changes: SimpleChanges) {
    if (this.chart) {
      this.updateChart();
    }
  }
  updateChart() {
    this.chart.data.labels = this.data.map((item) => item.date);
    this.chart.data.datasets[0].data = this.data.map((item) =>
      item.value.toString()
    );
    this.chart.update();
  }
}
