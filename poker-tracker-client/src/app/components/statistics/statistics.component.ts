import {
  Component,
  EventEmitter,
  Input,
  OnInit,
  SimpleChanges,
} from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Color, ScaleType, LegendPosition } from '@swimlane/ngx-charts';
import { StatisticsService } from 'src/app/services/statistics.service';
import { DialogComponent } from '../dialog/dialog.component';
import { GenericStatDTO } from 'src/app/model/genericStatDto';

@Component({
  selector: 'app-statistics',
  templateUrl: './statistics.component.html',
  styleUrls: ['./statistics.component.css'],
})
export class StatisticsComponent implements OnInit {
  @Input()
  public selectedTabIndex: number;
  @Input()
  public pageSelected: EventEmitter<string>;
  constructor(
    private statisticsService: StatisticsService,
    private dialog: MatDialog
  ) {
    this.loadGenericStats();
    this.loadYearlyResultStats();
    this.loadMonthlyResultStats();
  }
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
    let defCurr =
      localStorage.getItem('poker_tracker_defaultcurrency') !== null
        ? localStorage.getItem('poker_tracker_defaultcurrency')
        : '';

    if (value.value < 0) {
      return (
        -1 * (Math.round(Math.abs(value.value) * 100) / 100) + ' ' + defCurr
      );
    }
    return Math.round(Math.abs(value.value) * 100) / 100 + ' ' + defCurr;
  }
  playedTimeValueFormatting(value: any): string {
    const hours = Math.floor(value.value / 60);
    const minutes = value.value % 60;
    return hours + ' hours ' + minutes + ' minutes';
  }

  // vertical bar chart from here
  multi: any[] = [];
  monthly: any[] = [];

  type = [
    {
      name: 'Cash',
      value: 0,
    },
    {
      name: 'Tournament',
      value: 0,
    },
  ];

  tables: any = [];

  // options
  showXAxis: boolean = true;
  showYAxis: boolean = true;
  gradient: boolean = false;
  showLegend: boolean = true;
  showXAxisLabel: boolean = true;
  xAxisLabel: string = 'Year';
  xAxisLabelMonthly: string = 'Month';
  showYAxisLabel: boolean = true;
  yAxisLabel: string = 'Result HUF';
  animations: boolean = true;

  // options
  showLabels: boolean = true;
  isDoughnut: boolean = false;
  legendPosition: LegendPosition = LegendPosition.Right;

  loadGenericStats = () => {
    this.statisticsService.getGenericStats().subscribe({
      next: (stats) => {
        this.formatResult(stats);
        this.formatPlayedTimes(stats);
        this.calculateWages();
        this.type[0].value = stats.numberOfCashGames;
        this.type[1].value = stats.numberOfTournaments;
        this.assignTableStat(stats);
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
  loadYearlyResultStats = () => {
    this.statisticsService.getYearlyResults().subscribe({
      next: (stats: any) => {
        this.multi = stats;
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
  loadMonthlyResultStats = () => {
    this.statisticsService.getMonthlyResults().subscribe({
      next: (stats: any) => {
        this.monthly = stats;
        console.log(this.monthly);
        this.sortByMonth(this.monthly);
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
    if (this.playedTimes[0].value > 0) {
      this.wage[0].value =
        this.results[0].value / (this.playedTimes[0].value / 60);
    } else {
      this.wage[0].value = 0;
    }
    if (this.playedTimes[1].value > 0) {
      this.wage[1].value =
        this.results[1].value / (this.playedTimes[1].value / 60);
    } else {
      this.wage[1].value = 0;
    }
    if (this.playedTimes[2].value > 0) {
      this.wage[2].value =
        this.results[2].value / (this.playedTimes[2].value / 60);
    } else {
      this.wage[2].value = 0;
    }
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

  assignTableStat(stat: GenericStatDTO) {
    if (stat.numberOfTableSize2 > 0) {
      this.tables.push({ name: 'Max-2', value: stat.numberOfTableSize2 });
    }
    if (stat.numberOfTableSize3 > 0) {
      this.tables.push({ name: 'Max-3', value: stat.numberOfTableSize3 });
    }
    if (stat.numberOfTableSize4 > 0) {
      this.tables.push({ name: 'Max-4', value: stat.numberOfTableSize4 });
    }
    if (stat.numberOfTableSize5 > 0) {
      this.tables.push({ name: 'Max-5', value: stat.numberOfTableSize5 });
    }
    if (stat.numberOfTableSize6 > 0) {
      this.tables.push({ name: 'Max-6', value: stat.numberOfTableSize6 });
    }
    if (stat.numberOfTableSize7 > 0) {
      this.tables.push({ name: 'Max-7', value: stat.numberOfTableSize7 });
    }
    if (stat.numberOfTableSize8 > 0) {
      this.tables.push({ name: 'Max-8', value: stat.numberOfTableSize8 });
    }
    if (stat.numberOfTableSize9 > 0) {
      this.tables.push({ name: 'Max-9', value: stat.numberOfTableSize9 });
    }
    if (stat.numberOfTableSize10 > 0) {
      this.tables.push({ name: 'Max-10', value: stat.numberOfTableSize10 });
    }
  }
  ngOnChanges(changes: SimpleChanges) {
    setTimeout(() => {
      let result = document.getElementsByClassName('value-text');

      for (let i = 0; i < result.length; i++) {
        result.item(i)?.setAttribute('y', '64');
      }
    }, 1000);
  }

  sortByMonth(arr: any[]) {
    var months = [
      'January',
      'February',
      'March',
      'April',
      'May',
      'June',
      'July',
      'August',
      'September',
      'October',
      'November',
      'December',
    ];
    arr.sort((a: any, b: any) => {
      return months.indexOf(b.name) - months.indexOf(a.name);
    });

    let shift = 11 - new Date().getMonth();
    for (let i = 0; i < shift; i++) {
      arr.push(arr.shift());
    }
  }
  ngOnInit(): void {
    this.pageSelected.subscribe((e) => {
      if (e === 'statistics') {
        this.multi = [];
        this.monthly = [];
        this.tables = [];

        this.type[0].value = 0;
        this.type[1].value = 0;

        this.results[0].value = 0;
        this.results[1].value = 0;
        this.results[2].value = 0;
        this.playedTimes[0].value = 0;
        this.playedTimes[1].value = 0;
        this.playedTimes[2].value = 0;
        this.wage[0].value = 0;
        this.wage[1].value = 0;
        this.wage[2].value = 0;

        this.loadGenericStats();
        this.loadYearlyResultStats();
        this.loadMonthlyResultStats();
      }
    });
  }
}
