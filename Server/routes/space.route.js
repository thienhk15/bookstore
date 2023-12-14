import express from "express";
import SpaceController from "../controllers/space.controller.js";

const router = express.Router();

router.get("/spaces", SpaceController.getAll);
router.get("/space/:id", SpaceController.getById);
router.post("/space", SpaceController.create);
router.put("/space/:id", SpaceController.update);
router.delete("/space/:id", SpaceController.delete);

export default router;
