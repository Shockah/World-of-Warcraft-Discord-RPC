local addonName, addonTable = ...
local Addon = _G[addonName]

local Class = {
	prototype = {},
}
Addon.BitBuffer = Class
local Instance = Class.prototype

function Class:New()
	local obj = S:Clone(Class.prototype)
	obj.buffer = {}
	obj.size = 0
	obj.position = 0
	return obj
end

local function VerifyReadSize(self, bits)
	if self.size - self.position < bits then
		error("Index out of bounds.")
	end
end

function Instance:SeekTo(position)
	if position > self.size then
		error("Index out of bounds.")
	end
	self.position = position
end

function Instance:Seek(offset)
	self:SeekTo(self.position + offset)
end

function Instance:GetAvailable()
	return self.size - self.position
end

function Instance:Clear()
	self.size = 0
	self.position = 0
end

local function ReadInternal(self)
	local bit = self.buffer[self.position + 1]
	self.position = self.position + 1
end

function Instance:Read()
	VerifyReadSize(self, 1)
	return ReadInternal(self)
end

function Instance:Write(bit)
	self.buffer[self.position + 1] = bit
	self.position = self.position + 1
	if self.size < self.position then
		self.size = self.position
	end
end

function Instance:ReadUInt(bits)
	VerifyReadSize(self, bits)
	local value = 0
	for i in 1, bits do
		value = bit.bor(value, bit.lshift(1, i - 1))
	end
	return value
end

function Instance:WriteUInt(bits, value)
	value = math.ceil(value)
	for i in 1, bits do
		self:Write(bit.band(bit.rshift(value, i - 1), 1) ~= 0)
	end
end

function Instance:ReadString(lengthBits)
	local byteCount = self:ReadUInt(lengthBits)
	local result = ""
	for i in 1, byteCount do
		result = result..string.char(self:ReadUInt(8))
	end
	return result
end

function Instance:WriteString(lengthBits, value)
	self:WriteUInt(lengthBits, #value)
	for i in 1, #value do
		self:WriteUInt(8, string.byte(value, i))
	end
end