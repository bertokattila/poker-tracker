import { GameType } from './addSessionDto';

export class Session {
  constructor(
    public id: number,
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
