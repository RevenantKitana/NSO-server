'use client';

import { useState, useEffect, useRef, useCallback } from 'react';

const REACTION_BUBBLES = [
  'Chào bạn! 🥷',
  'Chơi vui nhé! 🎮',
  'Phi lợi nhuận ❤️',
  'Ninja School! ✨',
  'Cố lên bạn ơi! 🔥',
  'Hoài niệm xưa! 🍃',
  'Cày cuốc vui vẻ! 🍥',
  'Mod by Khánh 🚀',
  'Gặp NPC Okanehashi nhé! 🎁'
];

export default function Mascot({
  size = 56,
  className = '',
  interactive = true,
}) {
  const containerRef = useRef(null);
  const [sheetType, setSheetType] = useState('directions'); // 'directions' | 'reactions'
  const [currentFrame, setCurrentFrame] = useState(4); // Frame 0..8 in 3x3 matrix (4 = center)
  const [bubble, setBubble] = useState('');
  const [isBouncing, setIsBouncing] = useState(false);
  const [particles, setParticles] = useState([]);

  const reactionTimerRef = useRef(null);
  const bubbleTimerRef = useRef(null);
  const rafRef = useRef(null);

  // Convert frame number (0..8) to col and row (3x3 grid)
  const col = currentFrame % 3;
  const row = Math.floor(currentFrame / 3);

  // In 3x3 grid with background-size: 300% 300%:
  // col 0 = 0%, col 1 = 50%, col 2 = 100%
  // row 0 = 0%, row 1 = 50%, row 2 = 100%
  const bgPosX = col === 0 ? '0%' : col === 1 ? '50%' : '100%';
  const bgPosY = row === 0 ? '0%' : row === 1 ? '50%' : '100%';

  // Global trigger for reaction on screen-wide click
  const triggerReaction = useCallback(() => {
    if (!interactive) return;

    // Switch sheet to reactions
    setSheetType('reactions');

    // Pick random reaction frame (0..8)
    const randomFrame = Math.floor(Math.random() * 9);
    setCurrentFrame(randomFrame);

    // Pick random bubble text
    const randomText = REACTION_BUBBLES[Math.floor(Math.random() * REACTION_BUBBLES.length)];
    setBubble(randomText);

    // Trigger bounce scale
    setIsBouncing(true);

    // Spawn mini sparkles around mascot
    const newParticles = Array.from({ length: 4 }, (_, i) => ({
      id: Date.now() + i,
      x: (Math.random() - 0.5) * 50,
      y: -15 - Math.random() * 25,
      icon: ['✨', '⭐', '❤️', '🍥', '🔥'][i % 5],
    }));
    setParticles(newParticles);

    // Reset bounce
    setTimeout(() => setIsBouncing(false), 200);

    // Reset reaction timer and bubble timer
    if (reactionTimerRef.current) clearTimeout(reactionTimerRef.current);
    if (bubbleTimerRef.current) clearTimeout(bubbleTimerRef.current);

    bubbleTimerRef.current = setTimeout(() => {
      setBubble('');
      setParticles([]);
    }, 1800);

    // Return to direction tracking after 1200ms
    reactionTimerRef.current = setTimeout(() => {
      setSheetType('directions');
      setCurrentFrame(4); // neutral center
    }, 1200);
  }, [interactive]);

  // Global mousemove handler for smooth 3x3 direction tracking
  const handleMouseMove = useCallback(
    (e) => {
      if (!interactive || sheetType === 'reactions' || !containerRef.current) {
        return;
      }

      if (rafRef.current) cancelAnimationFrame(rafRef.current);

      rafRef.current = requestAnimationFrame(() => {
        if (!containerRef.current) return;
        const rect = containerRef.current.getBoundingClientRect();
        const centerX = rect.left + rect.width / 2;
        const centerY = rect.top + rect.height / 2;

        const dx = e.clientX - centerX;
        const dy = e.clientY - centerY;
        const dist = Math.hypot(dx, dy);

        // Deadzone around center: look straight/neutral (frame 4)
        if (dist < 40) {
          setCurrentFrame(4);
          return;
        }

        // Calculate angle in degrees (-180 to 180)
        const angle = Math.atan2(dy, dx) * (180 / Math.PI);

        // 8-directional mapping according to 3x3 grid:
        // Row 0: Top-Left (0), Top (1), Top-Right (2)
        // Row 1: Left (3), Center (4), Right (5)
        // Row 2: Bottom-Left (6), Bottom (7), Bottom-Right (8)
        let targetFrame = 4;

        if (angle >= -157.5 && angle < -112.5) {
          targetFrame = 0; // Top-Left
        } else if (angle >= -112.5 && angle < -67.5) {
          targetFrame = 1; // Top
        } else if (angle >= -67.5 && angle < -22.5) {
          targetFrame = 2; // Top-Right
        } else if (angle >= -22.5 && angle < 22.5) {
          targetFrame = 5; // Right
        } else if (angle >= 22.5 && angle < 67.5) {
          targetFrame = 8; // Bottom-Right
        } else if (angle >= 67.5 && angle < 112.5) {
          targetFrame = 7; // Bottom
        } else if (angle >= 112.5 && angle < 157.5) {
          targetFrame = 6; // Bottom-Left
        } else {
          targetFrame = 3; // Left
        }

        setCurrentFrame(targetFrame);
      });
    },
    [interactive, sheetType]
  );

  // Listen to global mouse movement and global click anywhere on the screen
  useEffect(() => {
    if (!interactive) return;

    const handleGlobalClick = () => {
      triggerReaction();
    };

    window.addEventListener('mousemove', handleMouseMove, { passive: true });
    window.addEventListener('pointerdown', handleGlobalClick, { passive: true });

    return () => {
      window.removeEventListener('mousemove', handleMouseMove);
      window.removeEventListener('pointerdown', handleGlobalClick);
      if (rafRef.current) cancelAnimationFrame(rafRef.current);
      if (reactionTimerRef.current) clearTimeout(reactionTimerRef.current);
      if (bubbleTimerRef.current) clearTimeout(bubbleTimerRef.current);
    };
  }, [interactive, handleMouseMove, triggerReaction]);

  const currentImageUrl =
    sheetType === 'reactions'
      ? '/assets/icon/reactions.png'
      : '/assets/icon/directions.png';

  return (
    <div
      ref={containerRef}
      style={{ width: size, height: size }}
      className={`relative inline-block select-none cursor-pointer group shrink-0 ${className}`}
      title="Mascot NSO (Tương tác theo chuột và nhấp chuột toàn màn hình!)"
    >
      {/* Speech / Reaction Bubble */}
      {bubble && (
        <div className="absolute -top-10 left-1/2 -translate-x-1/2 z-30 pointer-events-none whitespace-nowrap animate-bounce">
          <div className="px-2.5 py-1 rounded-lg bg-slate-900/95 border border-orange-500/40 text-orange-300 text-[11px] font-bold shadow-xl backdrop-blur-md flex items-center gap-1">
            {bubble}
          </div>
          <div className="w-2 h-2 bg-slate-900 border-r border-b border-orange-500/40 rotate-45 mx-auto -mt-1" />
        </div>
      )}

      {/* Floating particles on click */}
      {particles.map((p) => (
        <span
          key={p.id}
          style={{
            transform: `translate(${p.x}px, ${p.y}px)`,
            transition: 'all 0.6s cubic-bezier(0.2, 0.8, 0.2, 1)',
          }}
          className="absolute left-1/2 top-1/2 -translate-x-1/2 -translate-y-1/2 text-xs pointer-events-none opacity-0 animate-ping"
        >
          {p.icon}
        </span>
      ))}

      {/* Mascot Outer Glow & Container */}
      <div
        className={`w-full h-full rounded-2xl bg-gradient-to-br from-orange-500/40 via-amber-500/20 to-red-500/40 p-[2px] shadow-lg shadow-orange-500/20 group-hover:shadow-orange-500/50 group-hover:scale-105 transition-transform duration-150 ${
          isBouncing ? 'scale-115 rotate-3' : ''
        }`}
      >
        <div className="w-full h-full bg-[#0d1117] rounded-[14px] overflow-hidden flex items-center justify-center relative backdrop-blur-sm border border-white/10">
          {/* Sprite Sheet Frame Display: NO transition on background-position to ensure instant clean frame snapping */}
          <div
            style={{
              width: '100%',
              height: '100%',
              backgroundImage: `url(${currentImageUrl})`,
              backgroundSize: '300% 300%',
              backgroundPosition: `${bgPosX} ${bgPosY}`,
              backgroundRepeat: 'no-repeat',
              transition: 'none',
              imageRendering: 'crisp-edges',
            }}
            className="w-full h-full"
          />
        </div>
      </div>
    </div>
  );
}
