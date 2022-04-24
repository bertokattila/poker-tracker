import { Component, OnInit } from '@angular/core';
import { Color, ScaleType } from '@swimlane/ngx-charts';

@Component({
  selector: 'app-statistics',
  templateUrl: './statistics.component.html',
  styleUrls: ['./statistics.component.css'],
})
export class StatisticsComponent implements OnInit {
  single: any[] = [
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
    {
      name: 'All Time Playing Time',
      value: 3232,
    },
    {
      name: 'Last Year Playing Time',
      value: 7700000,
    },
    {
      name: 'Last 30 Days Playing Time',
      value: 4300000,
    },
  ];
  view: [number, number] = [700, 400];

  colorScheme: Color = {
    name: 'myScheme',
    selectable: true,
    group: ScaleType.Ordinal,
    domain: ['#b22f5b', '#0f0', '#0ff'],
  };
  cardColor: string = '#424242';

  valueFormatting(value: any): string {
    return value.value + ' HUF';
  }

  constructor() {}

  ngOnInit(): void {}
}
