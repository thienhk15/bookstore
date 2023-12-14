import mongoose from "mongoose";

const Schema = mongoose.Schema;
const SurfaceSchema = new Schema({
  id: {
    type: Number,
  },
  format: {
    type: String,
  },
  width: {
    type: Number,
  },
  height: {
    type: Number,
  },
  imgUrl: {
    type: String,
  },
  space: {
    type: Schema.Types.ObjectId,
    ref: "spaces",
  },
});

export const Surface = mongoose.model("surfaces", SurfaceSchema);
