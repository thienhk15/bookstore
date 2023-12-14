import cors from "cors";
import dotenv from "dotenv";
import express from "express";
import { errorHandler, notFound } from "./helper/errorHandler.js";
import db from "./configs/db.js";
import spaceRoute from "./routes/space.route.js";
import surfaceRoute from "./routes/surface.route.js";
import reportRoute from "./routes/report.route.js";
dotenv.config();
const PORT = 3000;
const app = express();
const corsOptions = {
  origin: "http://localhost:" + PORT,
};
db();
const initializeExpress = (app) => {
  app.use(cors(corsOptions));
  app.use(express.json());
  app.use(express.urlencoded({ extended: true }));
};

initializeExpress(app);

app.use(spaceRoute);
app.use(surfaceRoute);
app.use(reportRoute);
// app.get("/", async (req, res) => {
//   const userId = "656eda93b8a4c119074cc6f0";
//   try {
//     const tasks = await Task.find({ userId }).select(
//       "-userId -createdAt -updatedAt -status -__v"
//     );
//     res.json(tasks);
//   } catch (error) {
//     console.error("Error fetching tasks:", error);
//     res.status(500).json({ error: "Internal Server Error" });
//   }
// });
// app.get("/getTask/:userId", async (req, res) => {
//   const userId = req.params.userId;
//   try {
//     const tasks = await Task.find({ userId }).select(
//       "-userId -createdAt -updatedAt -status -__v"
//     );
//     res.json(tasks);
//   } catch (error) {
//     console.error("Error fetching tasks:", error);
//     res.status(500).json({ error: "Internal Server Error" });
//   }
// });
// app.get("/getTask/:userId/search", async (req, res) => {
//   const userId = req.params.userId.toString();
//   const keyword = req.query.keyword.toString();
//   console.log(userId, keyword)
//   let query = { userId }
//   if(keyword){
//     query = {
//       ...query,
//       title: { $regex: new RegExp(keyword) } 
//     }
//   }

//   try {
//     const tasks = await Task.find(query).select(
//       "-userId -createdAt -updatedAt -status -__v"
//     );
//     res.json(tasks);
//   } catch (error) {
//     console.error("Error fetching tasks:", error);
//     res.status(500).json({ error: "Internal Server Error" });
//   }
// });
// app.post("/login", async (req, res) => {
//   const data = req.body;
//   try {
//     const user = await User.find({
//       username: data.username,
//       password: data.password,
//     });
//     if (!user) {
//       res.status(401).json({ error: "Unauthorize" });
//     }
//     req.user = user;
//     res.json(user);
//   } catch (error) {
//     console.error("Error fetching posts:", error);
//     res.status(500).json({ error: "Internal Server Error" });
//   }
// });

app.use(notFound);
app.use(errorHandler);

app.listen(PORT, () => {
  console.log("Server is running on port: " + PORT);
  console.log("http://localhost:" + PORT);
});
