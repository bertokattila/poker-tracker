export class GenericStatDTO {
  constructor(
    public id: number,
    public userId: number,
    public lastMonthResult: Number,
    public lastYearResult: Number,
    public allTimeResult: Number,
    public lastMonthPlayedTime: Number,
    public lastYearPlayedTime: Number,
    public allTimePlayedTime: Number
  ) {}
}
