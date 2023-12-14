import dotenv from "dotenv";
import mongoose from "mongoose";

dotenv.config();

export default async () => {
  try {
    mongoose.set("strictQuery", true);
    const conn = await mongoose.connect(
      "mongodb+srv://thien:thien123456@cluster0.qvwepnh.mongodb.net/"
    );
    console.log("MongoDB Connected: " + conn.connection.host);
  } catch (err) {
    console.log(err);
  }
};
