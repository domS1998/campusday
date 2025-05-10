import {UserModel} from './user.model';

export class StationListModel {
  stations!: StationModel[];
}

export interface StationModel {
  name: string;
  number: number;
  location: Location
}

export class StationModel {
  name: string = '';
  number: number = 0;
  location: Location = new Location();
}

export interface Location {
  image: FloorPlanImage;
  xcoordinate: number;
  ycoordinate: number;
}

export class Location {
  image: FloorPlanImage = FloorPlanImage.G20;
  xcoordinate: number = 0;
  ycoordinate: number = 0;
}

export enum FloorPlanImage {
  G20 = "G20",
  G21 = "G21",
  G22 = "G22",
}
