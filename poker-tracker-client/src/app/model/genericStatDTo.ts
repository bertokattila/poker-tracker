export class GenericStatDTO {
  constructor(
    public id: number,
    public userId: number,
    public lastMonthResult: number,
    public lastYearResult: number,
    public allTimeResult: number,
    public lastMonthPlayedTime: number,
    public lastYearPlayedTime: number,
    public allTimePlayedTime: number,
    public numberOfCashGames: number,
    public numberOfTournaments: number,
    public numberOfTableSize2: number,
    public numberOfTableSize3: number,
    public numberOfTableSize4: number,
    public numberOfTableSize5: number,
    public numberOfTableSize6: number,
    public numberOfTableSize7: number,
    public numberOfTableSize8: number,
    public numberOfTableSize9: number,
    public numberOfTableSize10: number
  ) {}
}
