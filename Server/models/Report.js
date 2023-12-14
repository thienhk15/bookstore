import mongoose from "mongoose";
import StateEnum from "../enum.js";
const Schema = mongoose.Schema;
const ReportSchema = new Schema({
  id: {
    type: Number,
  },
  surface: {
    type: Schema.Types.ObjectId,
    ref: "surfaces",
    require: true,
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
  report_date: {
    type: Date,
  },
  content: {
    type: String,
  },
  email: {
    type: String,
  },
  phone: {
    type: String,
  },
  state: {
    type: Number,
    enum: [StateEnum.PENDING, StateEnum.PROCESSING, StateEnum.COMPLETED],
    default: StateEnum.PENDING,
  },
},
{
  timestamps: true,
}
);

export const Report = mongoose.model("reports", ReportSchema);
