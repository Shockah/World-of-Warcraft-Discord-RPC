local addonName, addonTable = ...
local Addon = _G[addonName]
local S = LibStub:GetLibrary("ShockahUtils")

local Class = {
	prototype = {},
}
Addon.PixelRenderer = Class
local Instance = Class.prototype

local freeFrames = {}

local function New()
	local frame = CreateFrame("Frame", nil)
	S:CloneInto(Class.prototype, frame)
	frame.pixels = Addon.Array2D:New(128, 128)
	return frame
end

local function Reset(frame)
end

function Class:Get()
	local frame
	if S:IsEmpty(freeFrames) then
		frame = New()
	else
		frame = freeFrames[1]
		table.remove(freeFrames, 1)
	end
	Reset(frame)
	return frame
end

function Instance:Free()
	self:Hide()
	table.insert(freeFrames, self)
end

local function NewPixel(self)
	local pixel = self:CreateTexture()
	return pixel
end

local function GetPixel(self, x, y)
	local pixel = self.pixels:Get(x, y)
	if not pixel then
		pixel = self:CreateTexture()
		pixel:SetWidth(1)
		pixel:SetHeight(1)
		pixel:SetPoint("TOPLEFT", x, -y)
		self.pixels:Set(x, y, pixel)
	end
	return pixel
end

function Instance:SetupPixel(x, y, color)
	if not color and not self.pixels:Get(x, y) then
		return
	end

	local pixel = GetPixel(self, x, y)
	if color then
		pixel:SetColorTexture(color[1], color[2], color[3])
		pixel:Show()
	else
		pixel:Hide()
	end
end

function Instance:Display(array)
	for y in 1, array.height do
		for x in 1, array.width do
			self:SetupPixel(x - 1, y - 1, array:Get(x - 1, y - 1))
		end
	end
end