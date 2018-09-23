local addonName, addonTable = ...
local Addon = _G[addonName]

local Class = {
	prototype = {},
}
Addon.Array2D = Class
local Instance = Class.prototype

function Class:New(width, height, fillWith)
	local obj = S:Clone(Class.prototype)
	obj.array = {}
	obj.width = width
	obj.height = height

	if fillWith ~= nil then
		for y in 1, height do
			for x in 1, width do
				obj:Set(x - 1, y - 1, fillWith)
			end
		end
	end

	return obj
end

local function GetIndex(self, x, y)
	if x < 0 or x >= self.width or y < 0 or y >= self.height then
		error("X,Y out of bounds.")
	end
	return y * self.width + x
end

function Instance:Get(x, y)
	return self.array[GetIndex(self, x, y)]
end

function Instance:Set(x, y, value)
	self.array[GetIndex(self, x, y)] = value
end