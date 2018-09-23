local addonName, addonTable = ...

_G[addonName] = LibStub("AceAddon-3.0"):NewAddon(addonName, "AceEvent-3.0")
local Addon = _G[addonName]
local S = LibStub:GetLibrary("ShockahUtils")

function Addon:OnInitialize()
end

function Addon:OnEnable()
	-- C_Timer.After(0.0, function()
	-- end)
end