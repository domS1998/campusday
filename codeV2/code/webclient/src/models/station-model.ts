export interface StationModel {
  name: string;
  number: number;
  location: Location
}

export interface Location {
  image: FloorPlanImage;
  xcoordinate: number;
  ycoordinate: number;
}

export enum FloorPlanImage {
  G20 = "G20",
  G21 = "G21",
  G22 = "G22",
}
