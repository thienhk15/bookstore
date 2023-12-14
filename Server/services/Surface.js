import { Surface } from "../models/Surface.js";

const SurfaceService = {
  async getAll(filter, projection) {
    return await Surface.find(filter).select(projection);
  },

  async getById(id) {
    return await Surface.findById(id);
  },

  async create(objectData) {
    const newObject = new Surface(objectData);
    return await newObject.save();
  },

  async update(id, updateData) {
    return await Surface.findByIdAndUpdate(id, updateData, { new: true });
  },

  async delete(id) {
    return await Surface.findByIdAndDelete(id);
  },
};

export default SurfaceService;
