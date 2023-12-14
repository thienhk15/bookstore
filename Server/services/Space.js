import { Space } from "../models/Space.js";

const SpaceService = {
  async getAll(filter, projection) {
    return await Space.find(filter).select(projection);
  },
  // async getAll() {
  //   return await EditRequest.find();
  // },

  async getById(id) {
    return await Space.findById(id);
  },

  async create(objectData) {
    const newObject = new Space(objectData);
    return await newObject.save();
  },

  async update(id, updateData) {
    return await Space.findByIdAndUpdate(id, updateData, { new: true });
  },

  async delete(id) {
    return await Space.findByIdAndDelete(id);
  },
};

export default SpaceService;
