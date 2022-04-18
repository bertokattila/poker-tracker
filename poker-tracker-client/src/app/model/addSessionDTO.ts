export enum GameType {
  cash = 'cash',
  tournament = 'tournament',
}
export class AddsessionDTO {
  constructor(
    public type: GameType,
    public currency: string,
    public buyIn: number,
    public cashOut: number,
    public startDate: string,
    public endDate: string,
    public comment: string,
    public location: string
  ) {}
}
