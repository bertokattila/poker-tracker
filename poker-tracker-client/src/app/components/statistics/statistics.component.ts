import { Component, Input, OnInit } from '@angular/core';
import { Color, ScaleType } from '@swimlane/ngx-charts';

@Component({
  selector: 'app-statistics',
  templateUrl: './statistics.component.html',
  styleUrls: ['./statistics.component.css'],
})
export class StatisticsComponent implements OnInit {
  @Input()
  public selectedTabIndex: number;
  results: any[] = [
    {
      name: 'All Time Result',
      value: 8940000,
    },
    {
      name: 'Last Year Result',
      value: 5000000,
    },
    {
      name: 'Last 30 Days Result',
      value: 7200000,
    },
  ];
  playedTimes: any[] = [
    {
      name: 'All Time Playing Time',
      value: 23233,
    },
    {
      name: 'Last Year Playing Time',
      value: 2342,
    },
    {
      name: 'Last 30 Days Playing Time',
      value: 185,
    },
  ];
  wage: any[] = [
    {
      name: 'All Time Average Hourly Wage',
      value: 23233,
    },
    {
      name: 'Last Year Average Hourly Wage',
      value: 2342,
    },
    {
      name: 'Last 30 Days Average Hourly Wage',
      value: 185,
    },
  ];
  view: [number, number] = [1000, 220];

  colorScheme: Color = {
    name: 'myScheme',
    selectable: true,
    group: ScaleType.Ordinal,
    domain: ['#276FBF', '#009B72', '#b22f5b'],
  };
  cardColor: string = '#424242';

  resultsValueFormatting(value: any): string {
    return value.value + ' HUF';
  }
  playedTimeValueFormatting(value: any): string {
    const hours = Math.floor(value.value / 60);
    const minutes = value.value % 60;
    return hours + ' hours ' + minutes + ' minutes';
  }

  // vertical bar chart from here
  multi: any[] = [
    {
      name: '2022',
      series: [
        {
          name: 'Cash Game',
          value: 7300000,
        },
        {
          name: 'Tournament',
          value: 8940000,
        },
      ],
    },

    {
      name: '2021',
      series: [
        {
          name: 'Cash Game',
          value: -7870000,
        },
        {
          name: 'Tournament',
          value: 8270000,
        },
      ],
    },

    {
      name: '2020',
      series: [
        {
          name: 'Cash Game',
          value: 5000002,
        },
        {
          name: 'Tournament',
          value: 5800000,
        },
      ],
    },
  ];

  // options
  showXAxis: boolean = true;
  showYAxis: boolean = true;
  gradient: boolean = false;
  showLegend: boolean = true;
  showXAxisLabel: boolean = true;
  xAxisLabel: string = 'Year';
  showYAxisLabel: boolean = true;
  yAxisLabel: string = 'Result HUF';
  animations: boolean = true;

  constructor() {}

  ngOnInit(): void {}
}
