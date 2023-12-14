import express from "express";
import SurfaceController from "../controllers/surface.controller.js";

const router = express.Router();

router.get("/surfaces", SurfaceController.getAll);
router.get("/surface/:id", SurfaceController.getById);
router.post("/surface", SurfaceController.create);
router.put("/surface/:id", SurfaceController.update);
router.delete("/surface/:id", SurfaceController.delete);

export default router;
