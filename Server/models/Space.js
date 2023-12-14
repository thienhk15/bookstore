import mongoose from "mongoose";

const Schema = mongoose.Schema;
const SpaceSchema = new Schema({
  id: {
    type: Number,
  },
  address: {
    type: String,
  },
  long: {
    type: Number,
  },
  lat: {
    type: Number,
  },
  type: {
    type: String,
  },
  format: {
    type: String,
  },
  ward: {
    type: String,
  },
});

export const Space = mongoose.model("spaces", SpaceSchema);
