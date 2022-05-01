import { Component, Input, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Color, ScaleType } from '@swimlane/ngx-charts';
import { StatisticsService } from 'src/app/services/statistics.service';
import { DialogComponent } from '../dialog/dialog.component';

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
      value: 0,
    },
    {
      name: 'Last Year Result',
      value: 0,
    },
    {
      name: 'Last 30 Days Result',
      value: 0,
    },
  ];
  playedTimes: any[] = [
    {
      name: 'All Time Playing Time',
      value: 0,
    },
    {
      name: 'Last Year Playing Time',
      value: 0,
    },
    {
      name: 'Last 30 Days Playing Time',
      value: 0,
    },
  ];
  wage: any[] = [
    {
      name: 'All Time Average Hourly Wage',
      value: 0,
    },
    {
      name: 'Last Year Average Hourly Wage',
      value: 0,
    },
    {
      name: 'Last 30 Days Average Hourly Wage',
      value: 0,
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
    return Math.round(Math.abs(value.value) * 100) / 100 + ' HUF';
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
  monthly: any[] = [
    {
      name: 'June',
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
      name: 'July',
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
      name: 'August',
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
    {
      name: 'September',
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
    {
      name: 'October',
      series: [
        {
          name: 'Cash Game',
          value: 654645,
        },
        {
          name: 'Tournament',
          value: 456546,
        },
      ],
    },
    {
      name: 'November',
      series: [
        {
          name: 'Cash Game',
          value: 6546465,
        },
        {
          name: 'Tournament',
          value: 546456,
        },
      ],
    },
    {
      name: 'December',
      series: [
        {
          name: 'Cash Game',
          value: 5345345,
        },
        {
          name: 'Tournament',
          value: 54334,
        },
      ],
    },
    {
      name: 'January',
      series: [
        {
          name: 'Cash Game',
          value: 312312,
        },
        {
          name: 'Tournament',
          value: 321312,
        },
      ],
    },
    {
      name: 'February',
      series: [
        {
          name: 'Cash Game',
          value: 878,
        },
        {
          name: 'Tournament',
          value: 678678,
        },
      ],
    },
    {
      name: 'March',
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
    {
      name: 'April',
      series: [
        {
          name: 'Cash Game',
          value: -660000,
        },
        {
          name: 'Tournament',
          value: 686,
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

  constructor(
    private statisticsService: StatisticsService,
    private dialog: MatDialog
  ) {
    this.loadGenericStats();
  }
  loadGenericStats = () => {
    this.statisticsService.getGenericStats().subscribe({
      next: (stats) => {
        this.formatResult(stats);
        this.formatPlayedTimes(stats);
        this.calculateWages();
      },
      error: (e) => {
        if (e.status === 400) {
          this.openDialog('An error occured', 'Unknown error');
        } else if (e.status === 404) {
          this.formatNAs();
        }
      },
    });
  };
  formatResult = (result: any) => {
    this.results[0].value = result.allTimeResult;
    this.results[1].value = result.lastYearResult;
    this.results[2].value = result.lastMonthResult;
  };
  formatPlayedTimes = (result: any) => {
    this.playedTimes[0].value = result.allTimePlayedTime;
    this.playedTimes[1].value = result.lastYearPlayedTime;
    this.playedTimes[2].value = result.lastMonthPlayedTime;
  };
  calculateWages = () => {
    this.wage[0].value =
      this.results[0].value / (this.playedTimes[0].value / 60);
    this.wage[1].value =
      this.results[1].value / (this.playedTimes[1].value / 60);
    this.wage[2].value =
      this.results[2].value / (this.playedTimes[2].value / 60);
  };
  formatNAs = () => {
    this.results[0].value = 0;
    this.results[1].value = 0;
    this.results[2].value = 0;
    this.playedTimes[0].value = 0;
    this.playedTimes[1].value = 0;
    this.playedTimes[2].value = 0;
    this.wage[0].value = 0;
    this.wage[1].value = 0;
    this.wage[2].value = 0;
  };

  openDialog(title: string, desc: string) {
    this.dialog.open(DialogComponent, {
      data: {
        title: title,
        description: desc,
      },
    });
  }

  ngOnInit(): void {}
}
