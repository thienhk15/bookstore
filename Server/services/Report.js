import { Report } from "../models/Report.js";

const ReportService = {
  async getAll(filter, projection) {
    return await Report.find(filter).select(projection);
  },
  // async getAll() {
  //   return await EditRequest.find();
  // },

  async getById(id) {
    return await Report.findById(id);
  },

  async create(objectData) {
    const newObject = new Report(objectData);
    return await newObject.save();
  },

  async update(id, updateData) {
    return await Report.findByIdAndUpdate(id, updateData, { new: true });
  },

  async delete(id) {
    return await Report.findByIdAndDelete(id);
  },
};

export default ReportService;
