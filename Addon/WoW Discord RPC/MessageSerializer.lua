local addonName, addonTable = ...
local Addon = _G[addonName]

local Class = {
	Version = 1,
}
Addon.MessageSerializer = Class

function Class:Serialize()
	local bits = Addon.BitBuffer:New()
	bits:WriteUInt(10, self.Version)

	if self:IncludeCharacter() then
		bits:Write(true)
		Addon.CharacterSerializer:Serialize(bits)
	else
		bits:Write(false)
	end

	if self:IncludeActivity() then
		bits:Write(true)
		Addon.ActivitySerializer:Serialize(bits)
	else
		bits:Write(false)
	end

	return bits
end

function Class:IncludeCharacter()
	return true
end

function Class:IncludeActivity()
	return false
end