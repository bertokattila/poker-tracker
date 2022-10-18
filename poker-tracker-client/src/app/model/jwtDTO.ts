export class JwtDTO {
  constructor(
    public jwt: string,
    public userName: string,
    public defaultCurrency: string
  ) {}
}
