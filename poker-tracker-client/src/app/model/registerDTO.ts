export class RegisterDTO {
  constructor(
    public email: string,
    public name: string,
    public password: string,
    public defaultCurrency: string
  ) {}
}
