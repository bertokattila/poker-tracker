export enum GameType {
  cash = 'cash',
  tournament = 'tournament',
}
export enum AccessType {
  public = 'public',
  private = 'private',
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
    public location: string,
    public access: AccessType
  ) {}
}
